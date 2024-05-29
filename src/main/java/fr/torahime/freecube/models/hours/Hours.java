package fr.torahime.freecube.models.hours;

public enum Hours {

    ZERO(18000, "00h00", new int[]{24,18,69}),
    ONE(19000, "01h00", new int[]{46,50,94}),
    TWO(20000, "02h00", new int[]{79,90,130}),
    THREE(21000, "03h00", new int[]{108,130,169}),
    FOUR(22000, "04h00", new int[]{133,156,188}),
    FIVE(23000, "05h00", new int[]{141,171,195}),
    SIX(24000, "06h00", new int[]{176,217,235}),
    SEVEN(1000, "07h00", new int[]{180,211,212}),
    EIGHT(2000, "08h00", new int[]{191,211,203}),
    NINE(3000, "09h00", new int[]{218,230,207}),
    TEN(4000, "10h00", new int[]{235,241,201}),
    ELEVEN(5000, "11h00", new int[]{237,242,178}),
    TWELVE(6000, "12h00", new int[]{255,255,255}),
    THIRTEEN(7000, "13h00", new int[]{230,236,185}),
    FOURTEEN(8000, "14h00", new int[]{201,227,193}),
    FIFTEEN(9000, "15h00", new int[]{172,220,210}),
    SIXTEEN(10000, "16h00", new int[]{170,213,221}),
    SEVENTEEN(11000, "17h00", new int[]{125,209,240}),
    EIGHTEEN(12000, "18h00", new int[]{100,196,255}),
    NINETEEN(13000, "19h00", new int[]{84,165,216}),
    TWENTY(14000, "20h00", new int[]{74,137,188}),
    TWENTYONE(15000, "21h00", new int[]{57,106,168}),
    TWENTYTWO(16000, "22h00", new int[]{48,77,135}),
    TWENTYTHREE(17000, "23h00", new int[]{38,48,104});

    private final int tick;
    private final String hour;
    private final int[] rgb;

    Hours(int tick, String hour, int[] rgb) {
        this.tick = tick;
        this.hour = hour;
        this.rgb = rgb;
    }

    Hours(int tick, String hour) {
        this.tick = tick;
        this.hour = hour;
        this.rgb = new int[]{0,0,0};
    }

    public static Hours getHourFromTick(int tick) {
        for (Hours hour : Hours.values()) {
            if (hour.getTick() == tick) {
                return hour;
            }
        }
        return null;
    }

    public int getTick() {
        return tick;
    }

    public String getHour() {
        return hour;
    }

    public int[] getRgb() {
        return rgb;
    }
}
