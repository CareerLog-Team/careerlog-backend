package careerlog.server.resume.domain;

public enum ItemType {
    BASIC_ITEM,
    MAIN_RESOURCE,
    EDUCATION,
    CAREER,
    WORK_PROJECT,
    SKILL,
    CERTIFICATE,
    LANGUAGE,
    LINK;

    public static ItemType fromValue(String value) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.name().equalsIgnoreCase(value)) {
                return itemType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + ItemType.class.getName() + " with value " + value);
    }
}
