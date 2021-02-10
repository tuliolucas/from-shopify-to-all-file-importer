package org.pushplussize.magalu.io.generate.type;

import org.pushplussize.magalu.io.FromTo;
import org.pushplussize.magalu.io.generate.type.product.MagaluProductTitle;

public interface ImporterType {
    FromTo getConfigByFrom(MagaluProductTitle magaluProductTitle);
    String getMagaluTitleDescription(Integer indexInRow);
    MagaluProductTitle getMagaluTitle(Integer indexInRow);

}
