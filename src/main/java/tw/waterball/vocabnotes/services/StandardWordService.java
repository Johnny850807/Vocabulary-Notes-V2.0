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

package tw.waterball.vocabnotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.waterball.vocabnotes.api.exceptions.BadRequestException;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.repositories.WordRepository;
import tw.waterball.vocabnotes.services.exceptions.ResourceNotFoundException;
import tw.waterball.vocabnotes.utils.RegexUtils;

import javax.transaction.Transactional;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Service
@Transactional
public class StandardWordService implements WordService {
    private WordRepository wordRepository;

    @Autowired
    public StandardWordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public Word createWord(Word word) {
        return wordRepository.save(word);
    }

    @Override
    public void changeImageUrlOfWord(String wordName, String imageUrl) {
        Word word = getWord(wordName);
        word.setImageUrl(imageUrl);
        if (!RegexUtils.isValidUrl(imageUrl)) {
            throw new BadRequestException("Invalid imageUrl: "+imageUrl);
        }
        wordRepository.save(word);
    }

    @Override
    public void updateWord(Word word) {
        wordRepository.save(word);
    }

    @Override
    public void deleteWord(String wordName) {
        wordRepository.deleteByName(wordName);
    }

    @Override
    public Word getWord(String wordName) {
        return wordRepository.findByName(wordName)
                .orElseThrow(()-> new ResourceNotFoundException("word", wordName));
    }
}
