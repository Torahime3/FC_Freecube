package fr.torahime.freecube.models.hours;

import org.bukkit.DyeColor;

public enum Hours {

    ZERO(18000, "00h00"),
    ONE(19000, "01h00"),
    TWO(20000, "02h00"),
    THREE(21000, "03h00"),
    FOUR(22000, "04h00"),
    FIVE(23000, "05h00"),
    SIX(24000, "06h00"),
    SEVEN(1000, "07h00"),
    EIGHT(2000, "08h00"),
    NINE(3000, "09h00"),
    TEN(4000, "10h00"),
    ELEVEN(5000, "11h00"),
    TWELVE(6000, "12h00"),
    THIRTEEN(7000, "13h00"),
    FOURTEEN(8000, "14h00"),
    FIFTEEN(9000, "15h00"),
    SIXTEEN(10000, "16h00"),
    SEVENTEEN(11000, "17h00"),
    EIGHTEEN(12000, "18h00"),
    NINETEEN(13000, "19h00"),
    TWENTY(14000, "20h00"),
    TWENTYONE(15000, "21h00"),
    TWENTYTWO(16000, "22h00"),
    TWENTYTHREE(17000, "23h00");


    private final int tick;
    private final String hour;

    Hours(int tick, String hour) {
        this.tick = tick;
        this.hour = hour;
    }

    public int getTick() {
        return tick;
    }

    public String getHour() {
        return hour;
    }



}
