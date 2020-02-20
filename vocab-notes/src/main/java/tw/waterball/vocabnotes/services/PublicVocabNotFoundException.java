package tw.waterball.vocabnotes.services;

import tw.waterball.vocabnotes.api.exceptions.NotFoundException;

public class PublicVocabNotFoundException extends NotFoundException {
    public PublicVocabNotFoundException(String resourceName, Object pk) {
        super(String.format("The public resource %s with primary key %s not found."
        , resourceName, pk));
    }
}
