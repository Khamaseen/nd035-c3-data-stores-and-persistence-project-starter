package com.udacity.jdnd.course3.critter.data;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// --> Probably better to use: @Enumerated(EnumType.STRING) ??
public enum PetFamily {

    CAT("CAT"),
    DOG("DOG"),
    LIZARD("LIZARD"),
    BIRD("BIRD"),
    FISH("FISH"),
    SNAKE("SNAKE"),
    OTHER("OTHER");

    private final String code;

    PetFamily(String code) {
        this.code = code;
    }

    public static PetFamily fromCode(String code) {
        if ( code.toUpperCase().equals(CAT.code)) {
            return CAT;
        }
        if ( code.toUpperCase().equals(DOG.code)) {
            return DOG;
        }
        if ( code.toUpperCase().equals(LIZARD.code)) {
            return LIZARD;
        }
        if ( code.toUpperCase().equals(BIRD.code)) {
            return BIRD;
        }
        if ( code.toUpperCase().equals(FISH.code)) {
            return FISH;
        }
        if ( code.toUpperCase().equals(SNAKE.code)) {
            return SNAKE;
        }
        if ( code.toUpperCase().equals(OTHER.code)) {
            return OTHER;
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!"
        );
    }

    public String getCode() {
        return code;
    }

    @Converter
    public static class PetFamilyConverter
            implements AttributeConverter<PetFamily, String> {

        public String convertToDatabaseColumn( PetFamily value ) {
            if ( value == null ) {
                return null;
            }

            return value.getCode();
        }

        public PetFamily convertToEntityAttribute( String value ) {
            if ( value == null ) {
                return null;
            }

            return PetFamily.fromCode( value );
        }
    }
}

// For additional details on using AttributeConverters, see JPA 2.1 AttributeConverters section.




