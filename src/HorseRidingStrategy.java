public class HorseRidingStrategy implements MovementStrategy {
    @Override
    public String move(Point from, Point to) {
        return "Герой едет на лошади из " + from + " в " + to + ".";
    }
}
