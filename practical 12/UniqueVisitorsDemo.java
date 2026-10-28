import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueVisitorsDemo {
    public static void main(String[] args) {
        List<String> loginLog = Arrays.asList(
                "hetvi_105", "aarav_21", "hetvi_105", "diya_44",
                "kunal_09", "aarav_21", "meera_77", "diya_44"
        );

        Set<String> uniqueUsers = new HashSet<>(loginLog);
        Set<String> orderedVisitors = new LinkedHashSet<>(loginLog);

        System.out.println("Raw Login Stream : " + loginLog);
        System.out.println("Total Logins     : " + loginLog.size());
        System.out.println("Unique Users (HashSet count)      : " + uniqueUsers.size());
        System.out.println("First-Seen Order (LinkedHashSet)  : " + orderedVisitors);

        System.out.print("First three distinct names        : ");
        Iterator<String> it = orderedVisitors.iterator();
        for (int i = 0; i < 3 && it.hasNext(); i++) {
            System.out.print(it.next() + (i < 2 ? ", " : ""));
        }
        System.out.println();
    }
}
