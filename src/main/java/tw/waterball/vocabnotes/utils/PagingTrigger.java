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

import tw.waterball.vocabnotes.utils.functional.TriConsumer;

import java.util.List;
import java.util.function.BiFunction;

/**
 * An util helps iterate the paging requests.
 * @author johnny850807@gmail.com (Waterball))
 */
public class PagingTrigger {

    public static <T> void perform(int startOffset, int fixedLimit,
                               BiFunction<Integer, Integer, List<T>> pager,
                               TriConsumer<Integer, Integer, List<T>> pageConsumer) {
        int offset = startOffset;
        List<T> page = pager.apply(offset, fixedLimit);

        while (page.size() == fixedLimit) {
            pageConsumer.accept(offset, fixedLimit, page);
            offset += fixedLimit;
            page = pager.apply(offset, fixedLimit);
        }

        if (!page.isEmpty()) {
            pageConsumer.accept(offset, fixedLimit, page);
        }
    }
}
