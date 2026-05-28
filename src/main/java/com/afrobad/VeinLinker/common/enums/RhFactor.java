package com.afrobad.VeinLinker.common.enums;

public enum RhFactor {
    POSITIVE, NEGATIVE;

    // MySQL stores '+' or '-' but Java enum names can't be symbols.
    // The @Column columnDefinition handles the DB side.
    // In your Service, map "+" → RhFactor.POSITIVE before saving.
	//OR, change enum in MySQL "POSITIVE" & "NEGATIVE"
}
