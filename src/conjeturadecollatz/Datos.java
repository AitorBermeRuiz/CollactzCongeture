package conjeturadecollatz;

import java.math.BigInteger;
import java.util.HashMap;

public class Datos {
    private BigInteger numMasAltoAlcanzado = new BigInteger("0"), numQueGeneraNumMasAltoAlcanzado = new BigInteger("0");
    private String numQueNoTerminanEn421 = "";
    
    private BigInteger contadorSecuencia = new BigInteger("0"), numQueGeneraSecuencia = new BigInteger("0");
    private String contenidoSecuencia;
    
    private BigInteger numeroAEjecutar = new BigInteger("0"), rangoMaximo = new BigInteger("0");
    private boolean pedirNumero = true;
    
    private HashMap <BigInteger, BigInteger> hashMap = new HashMap <BigInteger, BigInteger> ();
    
    //public Datos() {}
    
    public String getInfoNumMasAlto() {
        return numQueGeneraNumMasAltoAlcanzado+" -> "+numMasAltoAlcanzado;
    }
    
    public synchronized void setInfoNumMasAlto(BigInteger numMasAltoAlcanzado, BigInteger numQueGeneraNumMasAltoAlcanzado) {
        if (numMasAltoAlcanzado.compareTo(this.numMasAltoAlcanzado)>0) {
            this.numMasAltoAlcanzado = numMasAltoAlcanzado;
            this.numQueGeneraNumMasAltoAlcanzado = numQueGeneraNumMasAltoAlcanzado;
        }
    }
    
    public String getNumQueNoTerminanEn421() {
        if (numQueNoTerminanEn421.equals("")) return "No se ha encontrado ningún número";
        return numQueNoTerminanEn421;
    }
    
    public synchronized void setNumQueNoTerminanEn421(BigInteger numQueNoTerminanEn421) {
        //si encuentra algún numero que lo vaya añadiendo
        this.numQueNoTerminanEn421+= numQueNoTerminanEn421.toString()+", ";
    }
    
    public synchronized void setInfoSecuencia(BigInteger contadorSecuencia, BigInteger numQueGeneraSecuencia, String contenidoSecuencia) {

        if (contadorSecuencia.compareTo(this.contadorSecuencia)>0) {
            this.contadorSecuencia = contadorSecuencia;
            this.numQueGeneraSecuencia = numQueGeneraSecuencia;
            this.contenidoSecuencia = contenidoSecuencia;
            }
            
        }
    
    public String getInfoSecuencia() {
        return "Número "+numQueGeneraSecuencia+": Su secuencia es "+contadorSecuencia+" de larga.\n"+contenidoSecuencia;
    }
    
    public synchronized void setHashMap(BigInteger clave, BigInteger valor) {
        // guardaremos 
        hashMap.put(clave, valor);
        
    }//setHashMap()
    
    public HashMap<BigInteger, BigInteger> getHashMap() {
        return hashMap;
    }
    
    public void setRangoMinimoyMaximo(BigInteger numeroAEjecutar, BigInteger rangoMaximo){
        this.numeroAEjecutar = numeroAEjecutar;
        this.rangoMaximo = rangoMaximo;
    }
    
    public synchronized boolean getPedirNumero(){
        return pedirNumero;
    }
    
    public synchronized BigInteger getDarNumeroAHilo(){
        BigInteger provisional = new BigInteger(numeroAEjecutar.toString());
        this.numeroAEjecutar = this.numeroAEjecutar.add(BigInteger.ONE);
        if (provisional.compareTo(rangoMaximo)==0)
            pedirNumero = false;
        return provisional;
    }
    
}//Datos