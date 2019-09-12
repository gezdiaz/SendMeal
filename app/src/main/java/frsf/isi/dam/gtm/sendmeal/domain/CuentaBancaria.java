package frsf.isi.dam.gtm.sendmeal.domain;

public class CuentaBancaria {
    private Integer id;
    private String alias;
    private String cbu;

    public CuentaBancaria() {

    }
    public CuentaBancaria(Integer id, String alias, String cbu) {
        this.id = id;
        this.alias = alias;
        this.cbu = cbu;
    }

    public Integer getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getCbu() {
        return cbu;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CuentaBancaria that = (CuentaBancaria) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        return cbu != null ? cbu.equals(that.cbu) : that.cbu == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (cbu != null ? cbu.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", cbu='" + cbu + '\'' +
                '}';
    }

}
