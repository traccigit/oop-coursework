public class WalkingStrategy implements MovementStrategy {
    @Override
    public String move(Point from, Point to) {
        return "Герой идёт пешком из " + from + " в " + to + ".";
    }
}
