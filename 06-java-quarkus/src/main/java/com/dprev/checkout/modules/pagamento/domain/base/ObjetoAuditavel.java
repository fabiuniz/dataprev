package com.dprev.checkout.modules.pagamento.domain.base;

import io.quarkus.logging.Log;
import java.math.BigDecimal;

// [S] SRP: cuida somente de auditoria. Em Java toda classe já é "open" para herança.
public class ObjetoAuditavel {
    public void generateLogAuditoria(BigDecimal valor) {
        Log.infof("📝[LOG AUDITORIA] Transação registrada no valor de R$ %s", valor);
    }
}
