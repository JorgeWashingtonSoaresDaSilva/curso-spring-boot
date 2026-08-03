package com.jwss.studio.springboot.curso.service;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtilService  implements Serializable{

	private static final long serialVersionUID = 1L;
	// Retorna o PDF em Byte para download no navegador
	public byte[]gerarRelatorio(List listDados,String relatorio,
			ServletContext servletContext) throws Exception{
		// cria a lista de dados para relatórios com nossa lista de objetos para imprimir
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listDados);

		// Carregar o caminho do arquivo jasper compilado
		String caminhoJasper = servletContext.getRealPath("relatorios")+ File.separator + relatorio+
				".jasper";
		// Carregar arquivo Jasper passando os dados
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper,new HashMap<>(), jrBeanCollectionDataSource);
		// Exporta para byte[] para fazer download do PDF
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

}
