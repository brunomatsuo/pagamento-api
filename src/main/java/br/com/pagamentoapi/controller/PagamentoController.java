package br.com.pagamentoapi.controller;

import br.com.pagamentoapi.model.Pagamento;
import br.com.pagamentoapi.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pagamento")
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity efetuarPagamento(@RequestBody Pagamento pagamento) {
        pagamento = pagamentoService.efetuarPagamento(pagamento.getPedidoId(), pagamento.getFormaPagamento());

        if(pagamento != null) {
            return ResponseEntity.ok(pagamento);
        }
        return ResponseEntity.internalServerError().build();
    }
}
