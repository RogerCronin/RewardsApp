package APIObjects;

public class GetPointsResponse {
    boolean success;
    int points;
    public GetPointsResponse(boolean success, int points) {
        this.success = success;
        this.points = points;
    }
}
