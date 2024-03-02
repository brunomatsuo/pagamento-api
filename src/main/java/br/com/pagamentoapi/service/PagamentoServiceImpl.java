package br.com.pagamentoapi.service;

import br.com.pagamentoapi.enums.FormaPagamento;
import br.com.pagamentoapi.enums.StatusPagamento;
import br.com.pagamentoapi.model.Pagamento;
import br.com.pagamentoapi.repository.PagamentoRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${url.pedido}")
    private String urlPedido;

    @Override
    public Pagamento efetuarPagamento(Integer pedidoId, FormaPagamento formaPagamento) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(pedidoId);
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setStatusPagamento(StatusPagamento.EM_PROCESSAMENTO);

        try {
            pagamento = pagamentoRepository.save(pagamento);
            Boolean pedidoAtualizado = atualizarPedido(pagamento);

            return pagamento;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void processaPagamento() throws InterruptedException {

        List<Pagamento> pagamentosNaoProcessados = pagamentoRepository.findAll().stream().filter(pagamento -> pagamento.getStatusPagamento() == StatusPagamento.EM_PROCESSAMENTO).collect(Collectors.toList());

        for(Pagamento pagamento : pagamentosNaoProcessados) {
            if (pagamento.getFormaPagamento() == FormaPagamento.PIX || pagamento.getFormaPagamento() == FormaPagamento.DEBITO) {
                Thread.sleep(100);
            } else if (pagamento.getFormaPagamento() == FormaPagamento.CREDITO) {
                Thread.sleep(1000);
            } else {
                Thread.sleep(6000);
            }
            if (generateRandomNumber() > 30) {
                pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_EFETUADO);
            } else {
                pagamento.setStatusPagamento(StatusPagamento.FALHA_PAGAMENTO);
            }
            try {
                pagamentoRepository.save(pagamento);
                Boolean pedidoAtualizado = atualizarPedido(pagamento);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public Boolean atualizarPedido(Pagamento pagamento) {
        JSONObject json = new JSONObject();
        json.put("pedidoId", pagamento.getPedidoId());
        json.put("novoStatus", pagamento.getStatusPagamento());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .exchange(urlPedido, HttpMethod.PUT, entity, String.class);

        if(response.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        return true;
    }

    private int generateRandomNumber() {
        int min = 0;
        int max = 100;

        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
