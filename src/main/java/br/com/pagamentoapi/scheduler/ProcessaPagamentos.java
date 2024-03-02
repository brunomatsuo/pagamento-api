package br.com.pagamentoapi.scheduler;

import br.com.pagamentoapi.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProcessaPagamentos {

    @Autowired
    PagamentoService pagamentoService;

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void processarPagamentosPendentes() throws InterruptedException {
        pagamentoService.processaPagamento();
    }

}
