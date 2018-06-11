package negocio;

import java.io.File;
import java.io.FileOutputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;

public class UploadImagem {

		private String diretorio;
		private String caminho;
		private byte[] arquivo;
		private String nome;

		public UploadImagem() {
		}

		public String getNome() {
			return nome;
		}

		public String getRealPath() {
		ExternalContext externalContext = 
	                FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = 
	                (HttpServletResponse) externalContext.getResponse();
		
		FacesContext aFacesContext = FacesContext.getCurrentInstance();
		ServletContext context = 
	               (ServletContext) aFacesContext.getExternalContext().getContext();
		
		return context.getRealPath("/");
		}

		public void fileUpload(FileUploadEvent event, String type, String diretorio) {
			try {
				this.nome = new java.util.Date().getTime() + type;
				this.caminho = diretorio + getNome();
				this.arquivo = event.getFile().getContents();

				File file = new File(diretorio);
				file.mkdirs();

			} catch (Exception ex) {
				System.out.println("Erro no upload do arquivo" + ex);
			}
		}

		public void gravar() {

			try {

				FileOutputStream fos;
				fos = new FileOutputStream(this.caminho);
				fos.write(this.arquivo);
				fos.close();

			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
}

