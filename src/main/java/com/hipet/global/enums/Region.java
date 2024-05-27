package com.hipet.global.enums;

public enum Region {

    SEOUL("서울"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    DAEJEON("대전"),
    SEJONG("세종"),
    CHUNGNAM("충남"),
    CHUNGBUK("충북"),
    GWANGJU("광주"),
    JEOLLANAM("전남"),
    JEOLLABUK("전북"),
    DAEGU("대구"),
    GYEONGBUK("경북"),
    BUSAN("부산"),
    ULSAN("울산"),
    GYEONGNAM("경남"),
    GANGWON("강원"),
    JEJU("제주"),
    NATIONAL("전국");

    private final String displayName;

    Region(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
