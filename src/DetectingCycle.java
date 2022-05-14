import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class DetectingCycle {
    public boolean detectingUGCycle(Map<Integer, List<Integer>> adList) {
        if (adList == null || adList.size() == 0) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        for (int i : adList.keySet()) {
            if (!visited.contains(i)) {
                if (hasCycleUG(i, adList, visited)) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean hasCycleUG(int i, Map<Integer, List<Integer>> adList, Set<Integer> visited) {
        visited.add(i);
        for (int nei : adList.get(i)) {
            if (visited.contains(nei)) {
                return true;
            }
            if (hasCycleUG(nei, adList, visited)) {
                return true;
            }
        }
        return false;

    }

    public boolean detectingDGCycle(Map<Integer, List<Integer>> adList) {
        if (adList == null || adList.size() == 0) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        for (int i : adList.keySet()) {
            if (!visited.contains(i)) {
                Set<Integer> stack = new HashSet<>();
                if (hasCycleDG(i, adList, visited, stack)) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean hasCycleDG(int i, Map<Integer, List<Integer>> adList, Set<Integer> visited, Set<Integer> stack) {
        visited.add(i);
        stack.add(i);
        // expand
        for (int nei : adList.get(i)) {
            if (stack.contains(nei)) {
                return true;
            }
            if (!visited.contains(i) && hasCycleDG(nei, adList, visited, stack)) {
                return true;
            }
        }
        // finished, pop stack frame
        stack.remove(i);
        return false;
    }
}