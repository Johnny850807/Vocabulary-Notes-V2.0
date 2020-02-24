/*
 *  Copyright 2020 johnny850807 (Waterball)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tw.waterball.vocabnotes.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public class PermutationUtils {
    public static <T> List<List<T>> permutation(List<T> elements) {
        if (elements.size() == 1) {
            return Collections.singletonList(elements);
        }
        return permutation(elements, 0, new LinkedList<>());
    }

    private static <T> List<List<T>> permutation(List<T> elements, int pos, List<List<T>> permutation) {
        if(pos == elements.size()-1) {
            permutation.add(new LinkedList<>(elements));
        } else {
            permutation(elements, pos+1, permutation);
            for(int i = pos+1; i < elements.size(); i++) {
                Collections.swap(elements, pos, i);
                permutation(elements, pos+1, permutation);
                Collections.swap(elements, pos, i);
            }
        }
        return permutation;
    }

    public static void main(String[] args) {
        List<List<Integer>> permutation = PermutationUtils.permutation(Arrays.asList(1, 2, 3));
        for (List<Integer> p : permutation) {
            for (Integer num : p) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
