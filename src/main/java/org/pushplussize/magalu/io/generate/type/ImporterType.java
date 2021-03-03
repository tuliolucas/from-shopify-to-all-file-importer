package org.pushplussize.magalu.io.generate.type;

import org.pushplussize.magalu.io.FromTo;

public interface ImporterType {
    FromTo getConfigByFrom(Enum magaluHeaderTitleEnum);
    String getMagaluTitleDescription(Integer indexInRow);
    Enum getMagaluTitle(Integer indexInRow);

}
