package conjeturadecollatz;

import java.math.BigInteger;

public class Hilo implements Runnable{
    private BigInteger bigRangoMinimo, bigRangoMaximo;
    private BigInteger numMasAltoAlcanzado = new BigInteger("0"), numQueGeneraNumMasAlto = new BigInteger("0");
    private BigInteger contadorSecuencia   = new BigInteger("0"), numQueGeneraSecuencia  = new BigInteger("0");
    private BigInteger resultado 		   = new BigInteger("0"), resultado2 			 = new BigInteger("0");
    private String contenidoSecuencia = "";
    private int modo = 0;
    
    private Datos datos;
    
    //dependiendo del metodo de asignacion de numeros entrara a un constructor u otro.
    public Hilo(BigInteger bigRangoMinimo, BigInteger bigRangoMaximo, Datos datos) {
        this.bigRangoMinimo = bigRangoMinimo;
        this.bigRangoMaximo = bigRangoMaximo;
        this.datos = datos;
    }
    
    public Hilo(int modo, Datos datos) {
        this.modo = modo;
        this.datos = datos;
    }
    
    @Override
    public void run() {
    	// 0 = por rangos, 1 = numero a numero
        if      (modo == 0) hilosPorRango();
        else if (modo == 1) hilosNumeroPorNumero();
        
        // cuando termina el hilo carga los datos correspondientes
        datos.setInfoNumMasAlto(numMasAltoAlcanzado, numQueGeneraNumMasAlto);
        datos.setInfoSecuencia(contadorSecuencia, numQueGeneraSecuencia, contenidoSecuencia);
    }//run()
    
    private void hilosPorRango(){
        //si el primer numero introducido es un 0 le sumamos uno para evitar errores, ya que con el numero 0 al multiplicarlo da 0 como es evidente
        if (bigRangoMinimo.compareTo(BigInteger.ZERO)==0) bigRangoMinimo = bigRangoMinimo.add(BigInteger.ONE);
        while(bigRangoMinimo.compareTo(bigRangoMaximo)<=0){
            resultado  = bigRangoMinimo;
            resultado2 = bigRangoMinimo;
            ejecucionDentroDelWhile();
            bigRangoMinimo = bigRangoMinimo.add(BigInteger.ONE);
        }//while
    }// hilosPorRango()
    
    private void hilosNumeroPorNumero(){
        while(datos.getPedirNumero()){
            resultado  = datos.getDarNumeroAHilo();
            if (resultado.compareTo(BigInteger.ZERO)==0) resultado = resultado.add(BigInteger.ONE);
            resultado2 = resultado;
            ejecucionDentroDelWhile();
        }//while
    }// hilosNumeroPorNumero()
    
    private void ejecucionDentroDelWhile(){
    	BigInteger contadorSecuenciaTemporal  = new BigInteger("0");
        String     contenidoSecuenciaTemporal = resultado+",";
    	
        // este while realiza la operación matemática para llegar al fin de ciclo 4, 2, 1 o hallar si se ha explorado antes
        boolean reanudarBucle = true;
        if (resultado.equals(BigInteger.ONE)) reanudarBucle = false;// si el resultado es 1 no hace falta que se ejecute
        while (reanudarBucle) {
            // par = resultado / 2 || impar = (resultado * 3) + 1
            BigInteger resultadoTemporal;
            if (resultado.remainder(BigInteger.valueOf(2))==BigInteger.valueOf(0)) {
            	resultadoTemporal = resultado;
            	resultado = resultado.divide(BigInteger.valueOf(2));
            }else{
            	resultadoTemporal = resultado;
            	resultado = resultado.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE);
            }
            //si comentamos la linea posterior a esta, no guardará información en el hashMap y el número sefuirá hasta el fin de secuencia
            datos.setHashMap(resultadoTemporal, resultado);
            
            if (resultado.compareTo(numMasAltoAlcanzado)>0) {
                numMasAltoAlcanzado    = resultado;
                numQueGeneraNumMasAlto = resultado2;
            }
            contadorSecuenciaTemporal = contadorSecuenciaTemporal.add(BigInteger.ONE);
            contenidoSecuenciaTemporal += resultado+",";
            
            if((datos.getHashMap().containsKey(resultado)) || (resultado.equals(BigInteger.valueOf(1))))  {
                reanudarBucle = false;
            }
        }//while
        //System.out.println();
        ajustarSecuencia(contenidoSecuenciaTemporal, contadorSecuenciaTemporal); 
        //si encuentra un número que no termina en 1 se ejecuta el if, resultado guarda el ultimo número (siempre es 1)
        if (reanudarBucle) datos.setNumQueNoTerminanEn421(resultado2);
    }// ejecucionSecuenciaDeNumero()
    
    private void ajustarSecuencia(String contenidoSecuenciaTemporal, BigInteger contadorSecuenciaTemporal) {
        //Este metodo recorre el has map para sumarle el contador de iteraciones por numero y ademas agregarle al string de las secuencias de un numero los 
        //Numeros que le siguen en el HasMap
    	String componentesContenidoSecuenciaTemporal[] = contenidoSecuenciaTemporal.split(",");
        BigInteger ultimoNumeroSecuencia = new BigInteger(componentesContenidoSecuenciaTemporal[componentesContenidoSecuenciaTemporal.length-1]);
        boolean continuar = true;
        while (continuar) {
            if(ultimoNumeroSecuencia.compareTo(BigInteger.ONE)==0) continuar = false;
            else {
                ultimoNumeroSecuencia = datos.getHashMap().get(ultimoNumeroSecuencia);
                if (ultimoNumeroSecuencia == null)
                    ultimoNumeroSecuencia = BigInteger.ONE;
                contenidoSecuenciaTemporal+=ultimoNumeroSecuencia+",";
                contadorSecuenciaTemporal = contadorSecuenciaTemporal.add(BigInteger.ONE);
            }
        }
        
        if (contadorSecuenciaTemporal.compareTo(contadorSecuencia)>0) {
            contadorSecuencia     = contadorSecuenciaTemporal;
            numQueGeneraSecuencia = resultado2;
            contenidoSecuencia    = contenidoSecuenciaTemporal;
        }
        contenidoSecuenciaTemporal = "";
    }// ajustarSecuencia()
    
}//Hilo

























