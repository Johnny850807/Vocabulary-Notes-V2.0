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

package tw.waterball.vocabnotes.services.token;

import java.util.Date;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public interface TokenService {
    Date getDefaultExpirationTime();
    Token createToken(Params params);
    Token renewToken(String token);


    interface Token {
        Date getExpirationTime();
        int getMemberId();
        String toString();
    }

    interface Params {
        String getString(String key);
        int getInteger(String key);
    }
}
