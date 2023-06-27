package com.project.publication;

public enum PublicationType {
    Normal("normal", "default"),
    Book("book"),
    Poster("poster");

    private String type;
    private String backupType;

    PublicationType(String s, String backup) {
        type = s;
        backupType = backup;
    }

    PublicationType(String s){
        type = s;
    }

    public static PublicationType fromString(String s){
        for (PublicationType t : PublicationType.values()) {
            if (t.type.equalsIgnoreCase(s) || (t.backupType != null && t.backupType.equalsIgnoreCase(s))) {
                return t;
            }
        }
        System.out.println("Could not find corresponding type. Setting to default.");
        return PublicationType.Normal;
    }
}
