package vinnsla;

public class Floskur {
    private static final int FLASKA_PLASTIC_ISK = 15;
    private static final int FLASKA_GLASS_ISK = 20;
    private static final int DOS_ISK = 10;
    private int fjoldiPlasticFloskur;
    private int fjoldiGlassFloskur;
    private int fjoldiDosir;
    private int virdiFloskur;
    private int virdiDosir;
    private int samtalsFjoldi;
    private int samtalsVirdi;
    private int heildFjoldi;
    private int heildVirdi;

    /**
     * Sets the number of bottles and updates the total value based on the bottle type.
     *
     * @param floskur The number of bottles.
     * @param type    The type of bottle ("Plastic" or "Glass").
     */
    public void setFjoldiFloskur(int floskur, String type) {
        if ("Plastic".equals(type)) {
            fjoldiPlasticFloskur = floskur;
        } else {
            fjoldiGlassFloskur = floskur;
        }
        virdiFloskur = (fjoldiPlasticFloskur * FLASKA_PLASTIC_ISK) + (fjoldiGlassFloskur * FLASKA_GLASS_ISK);
        samtals();
    }

    /**
     * Sets the number of cans and updates their total value.
     *
     * @param dosir The number of cans.
     */
    public void setFjoldiDosir(int dosir) {
        fjoldiDosir = dosir;
        virdiDosir = fjoldiDosir * DOS_ISK;
        samtals();
    }

    /**
     * Gets the total value of bottles.
     *
     * @return The total value of bottles in ISK.
     */
    public int getISKFloskur() {
        return virdiFloskur;
    }

    /**
     * Gets the total value of cans.
     *
     * @return The total value of cans in ISK.
     */
    public int getISKDosir() {
        return virdiDosir;
    }

    /**
     * Gets the total number of bottles (plastic + glass).
     *
     * @return The total number of bottles.
     */
    public int getFjoldiFloskur() {
        return fjoldiPlasticFloskur + fjoldiGlassFloskur;
    }

    /**
     * Gets the number of cans.
     *
     * @return The number of cans.
     */
    public int getFjoldiDosir() {
        return fjoldiDosir;
    }

    /**
     * Gets the total count of items (cans + bottles) in the current session.
     *
     * @return The total count.
     */
    public int getSamtalsFjoldi() {
        return samtalsFjoldi;
    }

    /**
     * Gets the total value of items (cans + bottles) in the current session.
     *
     * @return The total value in ISK.
     */
    public int getSamtalsVirdi() {
        return samtalsVirdi;
    }

    /**
     * Gets the cumulative total count of items across all sessions.
     *
     * @return The cumulative total count.
     */
    public int getHeildFjoldi() {
        return heildFjoldi;
    }

    /**
     * Gets the cumulative total value of items across all sessions.
     *
     * @return The cumulative total value in ISK.
     */
    public int getHeildVirdi() {
        return heildVirdi;
    }

    /**
     * Calculates the total count and value for the current session.
     */
    private void samtals() {
        samtalsFjoldi = fjoldiPlasticFloskur + fjoldiGlassFloskur + fjoldiDosir;
        samtalsVirdi = virdiFloskur + virdiDosir;
    }

    /**
     * Adds the current session’s totals to the cumulative totals.
     */
    public void greida() {
        heildFjoldi += samtalsFjoldi;
        heildVirdi += samtalsVirdi;
    }

    /**
     * Resets the current session’s counts and values, but preserves cumulative totals.
     */
    public void hreinsa() {
        fjoldiPlasticFloskur = 0;
        fjoldiGlassFloskur = 0;
        fjoldiDosir = 0;
        virdiFloskur = 0;
        virdiDosir = 0;
        samtalsFjoldi = 0;
        samtalsVirdi = 0;
        // Do NOT reset heildFjoldi or heildVirdi
    }

    public void setDosir(int dosir) {
        setFjoldiDosir(dosir);  // notar réttu aðferðina sem reiknar virði og uppfærir fjölda
    }

    public void setFloskur(int floskur) {
        setFjoldiFloskur(floskur, "Plastic"); // eða "Glass" ef þú vilt
    }
}