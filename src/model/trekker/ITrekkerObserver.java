package model.trekker;

public interface ITrekkerObserver
{
    void signalUpdated(boolean isTracking);
    void positionUpdated(Trekker t);
    void targetReached(Trekker t);
    void routeEnded(Trekker t);
}