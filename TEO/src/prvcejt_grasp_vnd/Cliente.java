package prvcejt_grasp_vnd;

public class Cliente {

	private int id;
	private float coordenadaX;
	private float coordenadaY;
	private float demanda; //Se for maior que 0 = coleta se for menor que 0= entrega 
	private float iniciojt; // inicio da janela de tempo
	private float fimjt; // fim da janela de tempo
	private float tempoatendimento; //tempo total p atendimento de um cliente
	private float coleta;
	private float entrega;
	
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	public Cliente(int id,float coordenadaX,float coordenadaY,float demanda,float iniciojt, float fimjt,float tempoatendimento,float coleta,float entrega) {
		this.setId(id);
		this.setCoordenadaX(coordenadaX);
		this.setCoordenadaY(coordenadaY);
		this.setDemanda(demanda);
		this.setIniciojt(iniciojt);
		this.setFimjt(fimjt);
		this.setTempoatendimento(tempoatendimento);
		this.setColeta(coleta);
		this.setEntrega(entrega);
				
}


	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public float getCoordenadaX() {
		return coordenadaX;
	}


	public void setCoordenadaX(float coordenadaX) {
		this.coordenadaX = coordenadaX;
	}


	public float getCoordenadaY() {
		return coordenadaY;
	}


	public void setCoordenadaY(float coordenadaY) {
		this.coordenadaY = coordenadaY;
	}


	public float getDemanda() {
		return demanda;
	}


	public void setDemanda(float demanda) {
		this.demanda = demanda;
	}


	public float getIniciojt() {
		return iniciojt;
	}


	public void setIniciojt(float iniciojt) {
		this.iniciojt = iniciojt;
	}


	public float getFimjt() {
		return fimjt;
	}


	public void setFimjt(float fimjt) {
		this.fimjt = fimjt;
	}


	public float getTempoatendimento() {
		return tempoatendimento;
	}


	public void setTempoatendimento(float tempoatendimento) {
		this.tempoatendimento = tempoatendimento;
	}


	public float getColeta() {
		return coleta;
	}


	public void setColeta(float coleta) {
		this.coleta = coleta;
	}


	public float getEntrega() {
		return entrega;
	}


	public void setEntrega(float entrega) {
		this.entrega = entrega;
	}
}
