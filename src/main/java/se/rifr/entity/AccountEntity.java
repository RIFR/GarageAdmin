package se.rifr.entity;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.so4it.common.util.object.Required;
import com.so4it.component.entity.AbstractEntityBuilder;
import com.so4it.component.entity.IdEntity;

@SpaceClass
public class AccountEntity extends IdEntity<String> {

    private String barcode;

    private String bankId;

    private Double saldo;

    private String description;

    private Long   parkedTime; // minutes since last payment

    private AccountEntity() {}

    private AccountEntity(Builder builder) {
        this.barcode = Required.notNull(builder.barcode,"barcode",builder.isTemplate());
        this.bankId = Required.notNull(builder.bankId,"bankId",builder.isTemplate());
        this.saldo = Required.notNull(builder.saldo,"saldo",builder.isTemplate());
        this.description = builder.description;
        this.parkedTime = builder.parkedTime;
    }

    @Override
    @SpaceId(autoGenerate = false)
    @SpaceRouting
    public String getId() { return barcode; }
    private void setId(String id) { this.barcode = id; }


    public String getBarcode() {
        return barcode;
    }

    private void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBankId() {
        return bankId;
    }

    private void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Double getSaldo() {
        return saldo;
    }

    private void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    private String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Long getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(Long parkedTime) {
        this.parkedTime = parkedTime;
    }

    public static Builder builder() {
        return new Builder(false);
    }

    public static Builder templateBuilder() {
        return new Builder(true);
    }


    public static class Builder extends AbstractEntityBuilder<AccountEntity> {

        private String barcode;

        private String bankId;

        private Double saldo;

        private String description;

        private Long   parkedTime; // minutes since last payment

        public Builder(boolean template) {
            super(template);
        }

        public AccountEntity.Builder withBarcode(String barcode) {
            this.barcode = barcode;
            return this;
        }

        public AccountEntity.Builder withBankId(String bankId) {
            this.bankId = bankId;
            return this;
        }

        public AccountEntity.Builder withAmount(Double saldo) {
            this.saldo = saldo;
            return this;
        }

        public AccountEntity.Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public AccountEntity.Builder withParkedTime(Long parkedTime) {
            this.parkedTime = parkedTime;
            return this;
        }

        @Override
        public AccountEntity build() {
            return new AccountEntity(this);
        }
    }
}
