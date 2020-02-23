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

package tw.waterball.vocabnotes.models.entities;

import lombok.Data;

import java.util.HashMap;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Data
public class Level {
    private final static int[] LEVEL_TABLE = {
        0, 10, 50, 100, 200, 500, 1000, 2000, 4000, 8000, 10000, 30000, 80000, 150000,
            300000, 480000, 600000, 1000000, Integer.MAX_VALUE
    };
    private final static int MAX_LEVEL = LEVEL_TABLE.length-1;

    private int minExp;
    private int maxExp;
    private int number;
    private final static HashMap<Integer, Level> levelCache = new HashMap<>();

    private Level(int number) {
        this.number = number;
        if (number >= LEVEL_TABLE.length) {
            throw new IllegalArgumentException("The level number should not exceed " + LEVEL_TABLE.length);
        }
        minExp = LEVEL_TABLE[number-1];
        maxExp = LEVEL_TABLE[number]-1;
    }

    public static Level getLevelFromExp(int curExp) {
        curExp = Math.max(0, curExp);
        for (int lv = LEVEL_TABLE.length-1; lv >= 1; lv--) {
            if (LEVEL_TABLE[lv-1] <= curExp) {
                return getLevel(lv);
            }
        }
        return getLevel(1);
    }

    public static Level getLevel(int lv) {
        return levelCache.computeIfAbsent(lv, Level::new);
    }

    public static Level getMaxLevel() {
        return getLevel(MAX_LEVEL);
    }


}
