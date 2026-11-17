package dispatcher.policies;

import trip.Trip;

import java.util.List;

/**
 * The VIP First policy.
 * The trip with the lowest priority number goes first, since it is the most important.
 * Trips with the same priority number go in the order they joined the ready line.
 */
public class VIPFirst implements Policy {

    @Override
    public Trip pickNext(List<Trip> readyLine) 
    {
        Trip excursion = null;
        double lowest = -1;
        for(Trip t : readyLine)
        {
            if(lowest == -1)
            {
                excursion = t;
                lowest = t.getPriority();
                continue;
            }
            if(t.getPriority() < lowest)
            {
                excursion = t;
                lowest = t.getPriority();
            }
        }
        return excursion;
    }
}
