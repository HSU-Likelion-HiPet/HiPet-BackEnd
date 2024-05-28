package com.hipet.global.enums;

public enum Region {

    _SEOUL("서울"),
    _GYEONGGI("경기"),
    _INCHEON("인천"),
    _DAEJEON("대전"),
    _SEJONG("세종"),
    _CHUNGNAM("충남"),
    _CHUNGBUK("충북"),
    _GWANGJU("광주"),
    _JEONNAM("전남"),
    _JEONBUK("전북"),
    _DAEGU("대구"),
    _GYEONGBUK("경북"),
    _BUSAN("부산"),
    _ULSAN("울산"),
    _GYEONGNAM("경남"),
    _GANGWON("강원"),
    _JEJU("제주"),
    _ALL("전체");

    private final String displayName;

    Region(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
