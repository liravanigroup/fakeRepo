package pl.com.bottega.photostock.sales.model.users;

import pl.com.bottega.photostock.sales.model.users.Client.LanguageFormatter;

/**
 * Created on 03.04.2016.
 */
public enum ClientStatus {

    STANDARD(new String[]{"STANDARDOWY", "STANDARD"}),
    VIP(new String[]{"VIP", "VIP"}),
    GOLD(new String[]{"ZLOTY", "GOLD"}),
    SILVER(new String[]{"SREBRNY", "SILVER"}),
    PLATINUM(new String[]{"PLATYNOWY", "PLATINUM"});

    private String[] words;

    private ClientStatus(String[] clientStatus) {
        this.words = clientStatus;
    }

    public String getString(LanguageFormatter languare) {
        return words[languare.ordinal()];
    }

}
