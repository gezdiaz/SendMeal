package frsf.isi.dam.gtm.sendmeal.domain;

public enum EstadoPedido {
    PENDIENTE, ENVIADO, ACEPTADO, RECHAZADO, EN_PREPARACION, EN_ENVIO, ENTREGADO, CANCELADO;

    public static String[] getSpinnerList(){
        return new String[]{"Seleccione un estado...", ENVIADO.name(), ACEPTADO.name(), RECHAZADO.name(), EN_PREPARACION.name(), EN_ENVIO.name(), ENTREGADO.name()};
    }

}
