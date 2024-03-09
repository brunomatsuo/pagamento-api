# MS-Pagamento

Documentação: https://documenter.getpostman.com/view/7520874/2sA2xh2Y1E

Guia:

- Quando é efetuado um pedido no microsserviço de carrinho, este microsserviço recebe o pedido e a forma de pagamento;
- Uma vez recebido com sucesso, o status de pagamento do pedido é alterado para 'EM PROCESSAMENTO';
- O sistema tem um scheduler que roda a cada 30 segundos, que busca todos os pedidos no status 'EM PROCESSAMENTO' e "sorteia" se o pagamento será bem-sucedido ou não. Em seguida faz a atualização do status do pedido, chamando o MS-Carrinho.