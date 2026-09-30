public class FlyingStrategy implements MovementStrategy {
    @Override
    public String move(Point from, Point to) {
        return "Герой летит из " + from + " в " + to + ".";
    }
}
