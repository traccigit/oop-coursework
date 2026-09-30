import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.io.IOException;

public class TranslatorPanel extends TaskPanel {
    final JTextArea input=JellyTheme.area(true,5);
    final JTextArea dictionaryStatus=JellyTheme.area(false,2);
    final JComboBox<String> direction=new JComboBox<>(new String[]{"Иностранный → русский","Русский → иностранный"});
    private List<DictionaryEntry> entries;
    private final JellyTheme.Button translateButton;
    public TranslatorPanel() {
        super("Лабораторная работа 3","Подключите словарь или выберите уже готовый, после чего переведите текст в любом направлении.");
        addBlock(JellyTheme.row(button("Выбрать словарь…",false,() -> choose(true)), direction));
        dictionaryStatus.setText("Словарь не выбран. Формат: слово или выражение | перевод");
        addBlock(dictionaryStatus);
        addBlock(JellyTheme.label("ТЕКСТ ДЛЯ ПЕРЕВОДА",11,true)); addBlock(JellyTheme.scroll(input));
        translateButton=button("Перевести",true,this::translate);
        addBlock(JellyTheme.row(translateButton,button("Открыть текст…",false,() -> choose(false)),button("Очистить",false,() -> { input.setText(""); info(""); })));
        info("Регистр игнорируется. При совпадении нескольких выражений выбирается самое длинное.");
    }
    private void choose(boolean dictionary) {
        JFileChooser chooser=new JFileChooser(Files.isDirectory(Path.of("dictionaries"))?Path.of("dictionaries").toFile():null);
        chooser.setDialogTitle(dictionary?"Открыть словарь UTF-8":"Открыть текст UTF-8");
        chooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы (*.txt)","txt"));
        if(chooser.showOpenDialog(this)!=JFileChooser.APPROVE_OPTION)return;
        Path path=chooser.getSelectedFile().toPath();
        new SwingWorker<Object,Void>() {
            protected Object doInBackground() throws Exception {
                if(dictionary) return new DictionaryLoader().load(path);
                try {return Files.readString(path,StandardCharsets.UTF_8).replaceFirst("^\uFEFF","");}
                catch(IOException ex) {throw new FileReadException("Не удалось прочитать текстовый файл: "+path,ex);}
            }
            @SuppressWarnings("unchecked")
            protected void done() {
                try {
                    Object value=get();
                    if(dictionary) {
                        List<DictionaryEntry> loaded=(List<DictionaryEntry>)value;
                        if(loaded.isEmpty())throw new IllegalArgumentException("Выбранный словарь пуст.");
                        entries=loaded; dictionaryStatus.setText(path.getFileName()+"\nЗаписей: "+entries.size());
                        info("Словарь загружен. Введите текст или откройте файл.");
                    } else {input.setText((String)value); info("Текст загружен из "+path.getFileName()+".");}
                } catch(Exception ex) {Throwable cause=ex.getCause(); error(cause instanceof Exception?(Exception)cause:ex);}
            }
        }.execute();
    }
    void translate() {
        if(entries==null)throw new IllegalArgumentException("Сначала выберите файл словаря.");
        if(input.getText().isBlank())throw new IllegalArgumentException("Введите текст или откройте текстовый файл.");
        TranslationDirection d=direction.getSelectedIndex()==0?TranslationDirection.TO_RUSSIAN:TranslationDirection.FROM_RUSSIAN;
        Translator translator=new Translator(TranslationDictionary.create(entries,d));
        String text=input.getText(); translateButton.setEnabled(false); info("Перевожу…");
        new SwingWorker<String,Void>() {
            protected String doInBackground() {return translator.translate(text);}
            protected void done() {try {info(get());} catch(Exception ex) {error(ex);} finally {translateButton.setEnabled(true);}}
        }.execute();
    }
}
