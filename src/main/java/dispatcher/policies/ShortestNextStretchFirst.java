package dispatcher.policies;

import trip.Trip;

import java.util.List;

/**
 * The Shortest Next Stretch First policy.
 * The vehicle that is expected to need the driver for the fewest ticks will be driven next.
 * Trips with the same estimate go in the order they joined the ready line.
 * <p>
 * The expectation is estimated by exponential averaging with the given alpha, going through the trip's stretch
 * lengths ({@code trip.getStretchLengths()}) oldest first:
 * new estimate = alpha * old estimate + (1 - alpha) * length of the stretch.
 * A new trip's estimate starts at 0. If the old estimate is 0, the new estimate is the length of the stretch.
 * The trip's priority number is not used.
 */
public class ShortestNextStretchFirst implements Policy {

    private final double alpha;

    public ShortestNextStretchFirst(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public Trip pickNext(List<Trip> readyLine) {
        Trip guesstimate = null;
       for(Trip t : readyLine)
       {
        if(guesstimate == null)
        {
            guesstimate = t;
            continue;
        }
        if(getEstimate(guesstimate) > getEstimate(t))
            guesstimate = t;
       }
       return guesstimate;
    }

    /**
     * Returns the estimated length of the given trip's next stretch.
     *
     * @param trip the trip
     * @return the estimate, or 0 if the trip has not been driven yet
     */
    public double getEstimate(Trip trip) {
        double guessAverage = 0;
        for(double l : trip.getStretchLengths())
        {
            if(guessAverage == 0)
            {
                guessAverage = l;
                continue;
            }
            guessAverage = alpha * guessAverage + (1.0 - alpha) * l;
        }
        return guessAverage;
    }
}
