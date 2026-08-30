package com.ecommerce.model.enums;

public enum Currency {
    USD("United States Dollar", "$"),
    EUR("Euro", "€"),
    GBP("British Pound Sterling", "£"),
    JPY("Japanese Yen", "¥"),
    CAD("Canadian Dollar", "C$"),
    AUD("Australian Dollar", "A$"),
    CHF("Swiss Franc", "CHF"),
    CNY("Chinese Yuan", "¥"),
    INR("Indian Rupee", "₹"),
    BRL("Brazilian Real", "R$"),
    MXN("Mexican Peso", "Mex$"),
    KRW("South Korean Won", "₩"),
    SEK("Swedish Krona", "kr"),
    NOK("Norwegian Krone", "kr"),
    DKK("Danish Krone", "kr"),
    NZD("New Zealand Dollar", "NZ$"),
    SGD("Singapore Dollar", "S$"),
    HKD("Hong Kong Dollar", "HK$"),
    TRY("Turkish Lira", "₺"),
    RUB("Russian Ruble", "₽");

    private final String displayName;
    private final String symbol;

    Currency(String displayName, String symbol) {
        this.displayName = displayName;
        this.symbol = symbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }
}
