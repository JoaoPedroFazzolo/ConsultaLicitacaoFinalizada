package br.com.joaopedrofazzolo.itenspregao.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
public class CompraItemModel {
    private String idCompra;
    private String idCompraItem;
    private String idContratacaoPNCP;
    private String unidadeOrgaoCodigoUnidade;
    private String orgaoEntidadeCnpj;
    private int numeroItemPncp;
    private int numeroItemCompra;
    private int numeroGrupo;
    private String descricaoResumida;
    private String materialOuServico;
    private String materialOuServicoNome;
    private int codigoClasse;
    private int codigoGrupo;
    private int codItemCatalogo;
    private String descricaodetalhada;
    private String unidadeMedida;
    private boolean orcamentoSigiloso;
    private int itemCategoriaIdPncp;
    private String itemCategoriaNome;
    private int criterioJulgamentoIdPncp;
    private String criterioJulgamentoNome;
    private String situacaoCompraItem;
    private String situacaoCompraItemNome;
    private String tipoBeneficio;
    private String tipoBeneficioNome;
    private boolean incentivoProdutivoBasico;
    private float quantidade;
    private float valorUnitarioEstimado;
    private float valorTotal;
    private boolean temResultado;
    private String codFornecedor;
    private String nomeFornecedor;
    private float quantidadeResultado;
    private float valorUnitarioResultado;
    private float valorTotalResultado;
    private LocalDateTime dataInclusaoPncp;
    private LocalDateTime dataAtualizacaoPncp;
    private String dataResultado;
    private boolean margemPreferenciaNormal;
    private int percentualMargemPreferenciaNormal;
    private boolean margemPreferenciaAdicional;
    private int percentualMargemPreferenciaAdicional;
    private String codigoNCM;
    private String descricaoNCM;
}