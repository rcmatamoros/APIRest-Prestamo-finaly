package hn.unah.proyecto.enums;


import lombok.Getter;

@Getter
public enum PrestamoEnum {
    Vehicular('V'),
    Personal('P'),
    Hipotecario('H');

    private final char c;

    private PrestamoEnum(char c) {
        this.c = c;
    }


}
