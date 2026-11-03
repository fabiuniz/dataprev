package com.dprev.checkout.modules.pagamento.domain.base;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class ObjetoAuditavel {
    private static final Logger LOG = Logger.getLogger(ObjetoAuditavel.class.getName());

    public void generateLogAuditoria(BigDecimal valor) {
        LOG.info(String.format("📝[LOG AUDITORIA] Transação registrada no valor de R$ %s", valor));
    }
}