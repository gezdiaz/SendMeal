package frsf.isi.dam.gtm.sendmeal.domain;

public enum EstadoPedido {
    PENDIENTE, ENVIADO, ACEPTADO, RECHAZADO, EN_PREPARACION, EN_ENVIO, ENTREGADO, CANCELADO;

    public static EstadoPedido[] getSpinnerList(){
        return new EstadoPedido[]{ENVIADO, ACEPTADO, RECHAZADO, EN_PREPARACION, EN_ENVIO, ENTREGADO};
    }

}
