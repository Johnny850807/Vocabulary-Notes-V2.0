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

import org.apache.commons.validator.routines.UrlValidator;

import java.util.regex.Pattern;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public class RegexUtils {
    public final static String HTTP_URL_PATTERN_REGEX = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)";
    private static Pattern HTTP_URL_PATTERN;

    public static boolean isValidUrl(String url) {
        if (HTTP_URL_PATTERN == null) {
            HTTP_URL_PATTERN = Pattern.compile(HTTP_URL_PATTERN_REGEX);
        }
        UrlValidator defaultValidator = new UrlValidator();

        return HTTP_URL_PATTERN.matcher(url).matches();
    }
}
