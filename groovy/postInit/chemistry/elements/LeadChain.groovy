import globals.Globals
import globals.CarbonGlobals
import static globals.SinteringGlobals.*

FLUID_SOLIDIFIER = recipemap('fluid_solidifier')
EBF = recipemap('electric_blast_furnace')
CENTRIFUGE = recipemap('centrifuge')
ROASTER = recipemap('roaster')
REACTION_FURNACE = recipemap('reaction_furnace')
BR = recipemap('batch_reactor')
ELECTROLYTIC_CELL = recipemap('electrolytic_cell')
FLOTATION = recipemap('froth_flotation')
VACUUM_CHAMBER = recipemap('vacuum_chamber')
DISTILLERY = recipemap('distillery')
FLUID_EXTRACTOR = recipemap('extractor')
ELECTROLYZER = recipemap('electrolyzer')
SIFTER = recipemap('sifter')
ROTARY_KILN = recipemap('rotary_kiln')
CLARIFIER = recipemap('clarifier')

//REMOVAL
mods.gregtech.electric_blast_furnace.removeByInput(120, [metaitem('dustGalena')], [fluid('oxygen') * 3000])

//OPTIONAL FLOTATION
//GALENA
MIXER.recipeBuilder()
        .inputs(ore('dustImpureGalena') * 8)
        .fluidInputs(fluid('distilled_water') * 3000)
        .fluidOutputs(fluid('impure_galena_slurry') * 3000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FLOTATION.recipeBuilder()
        .fluidInputs(fluid('impure_galena_slurry') * 3000)
        .notConsumable(metaitem('dustSodiumEthylXanthate'))
        .notConsumable(fluid('sodium_cyanide_solution') * 100)
        .fluidOutputs(fluid('galena_slurry') * 1000)
        .fluidOutputs(fluid('galena_byproducts_slurry') * 2000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('galena_slurry') * 1000)
        .outputs(metaitem('dustGalena') * 16)
        .fluidOutputs(fluid('wastewater') * 1000)
        .EUt(Globals.voltAmps[1])
        .duration(20)
        .buildAndRegister()

FLOTATION.recipeBuilder()
        .fluidInputs(fluid('galena_byproducts_slurry') * 2000)
        .notConsumable(metaitem('dustSodiumEthylXanthate'))
        .fluidOutputs(fluid('sphalerite_byproduct_slurry') * 1000)
        .fluidOutputs(fluid('granite_tailing_slurry') * 1000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('sphalerite_byproduct_slurry') * 1000)
        .outputs(metaitem('dustSphalerite') * 2)
        .fluidOutputs(fluid('wastewater') * 1000)
        .EUt(Globals.voltAmps[1])
        .duration(20)
        .buildAndRegister()

//CERUSSITE
MIXER.recipeBuilder()
        .inputs(ore('dustImpureCerussite') * 8)
        .fluidInputs(fluid('distilled_water') * 2000)
        .fluidOutputs(fluid('impure_cerussite_slurry') * 2000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FLOTATION.recipeBuilder()
        .fluidInputs(fluid('impure_cerussite_slurry') * 2000)
        .notConsumable(metaitem('dustSodiumEthylXanthate'))
        .fluidOutputs(fluid('cerussite_slurry') * 1000)
        .fluidOutputs(fluid('granite_tailing_slurry') * 1000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('cerussite_slurry') * 1000)
        .outputs(metaitem('dustCerussite') * 16)
        .fluidOutputs(fluid('wastewater') * 1000)
        .EUt(Globals.voltAmps[1])
        .duration(20)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .inputs(ore('dustCerussite') * 1)
        .outputs(metaitem('dustMassicot') * 2)
        .fluidOutputs(fluid('carbon_dioxide') * 1000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

//ANGLESITE
MIXER.recipeBuilder()
        .inputs(ore('dustImpureAnglesite') * 8)
        .fluidInputs(fluid('distilled_water') * 2000)
        .fluidOutputs(fluid('impure_anglesite_slurry') * 2000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FLOTATION.recipeBuilder()
        .fluidInputs(fluid('impure_anglesite_slurry') * 2000)
        .notConsumable(metaitem('dustSodiumEthylXanthate'))
        .fluidOutputs(fluid('anglesite_slurry') * 1000)
        .fluidOutputs(fluid('granite_tailing_slurry') * 1000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('anglesite_slurry') * 1000)
        .outputs(metaitem('dustAnglesite') * 16)
        .fluidOutputs(fluid('wastewater') * 1000)
        .EUt(Globals.voltAmps[1])
        .duration(20)
        .buildAndRegister()

//CONCENTRATE SINTERING
for (fuel in rotary_kiln_fuels) {
    for (comburent in rotary_kiln_comburents) {
        ROTARY_KILN.recipeBuilder()
                .inputs(ore('dustMassicot') * 2)
                .outputs(metaitem('dustSinteredLeadConcentrate') * 2)
                .fluidInputs(fluid(fuel.name) * fuel.amountRequired)
                .fluidInputs(fluid(comburent.name) * comburent.amountRequired)
                .fluidOutputs(fluid(fuel.byproduct) * fuel.byproductAmount)
                .duration(fuel.duration + comburent.duration)
                .EUt(120)
                .buildAndRegister()

        ROTARY_KILN.recipeBuilder()
                .inputs(ore('dustGalena') * 1)
                .outputs(metaitem('dustSinteredLeadConcentrate') * 2)
                .fluidInputs(fluid(fuel.name) * fuel.amountRequired)
                .fluidInputs(fluid(comburent.name) * comburent.amountRequired)
                .fluidOutputs(fluid('sulfur_dioxide') * 1000)
                .duration(fuel.duration + comburent.duration)
                .EUt(120)
                .buildAndRegister()

        ROTARY_KILN.recipeBuilder()
                .inputs(ore('dustAnglesite') * 1)
                .outputs(metaitem('dustSinteredLeadConcentrate') * 2)
                .fluidInputs(fluid(fuel.name) * fuel.amountRequired)
                .fluidInputs(fluid(comburent.name) * comburent.amountRequired)
                .fluidOutputs(fluid('sulfur_trioxide') * 1000)
                .duration(fuel.duration + comburent.duration)
                .EUt(120)
                .buildAndRegister()
    }
}

//SINTER-ROAST PROCESS (UNIVERSAL, 200%)
def combustibles = CarbonGlobals.combustibles()

for (combustible in combustibles) {
    EBF.recipeBuilder()
            .inputs(ore('dustSinteredLeadConcentrate') * 2)
            .inputs(ore(combustible.name) * combustible.equivalent(1))
            .inputs(ore('dustTinyCalcite'))
            .outputs(metaitem('ingotCrudeLead') * 2)
            .outputs(metaitem(combustible.byproduct))
            .fluidOutputs(fluid('cadmium_rich_flue_gas') * 1000)
            .EUt(120)
            .blastFurnaceTemp(850)
            .duration(160)
            .buildAndRegister()
}

SIFTER.recipeBuilder()
        .notConsumable(metaitem('item_filter'))
        .fluidInputs(fluid('cadmium_rich_flue_gas') * 1000)
        .outputs(metaitem('dustCadmiumRichFlue'))
        .fluidOutputs(fluid('carbon_monoxide') * 1000)
        .EUt(120)
        .duration(160)
        .buildAndRegister()

//DECOPPERING
BR.recipeBuilder()
        .inputs(ore('dustAnyPuritySulfur'))
        .fluidInputs(fluid('crude_lead') * 3600)
        .outputs(metaitem('dustCopperDross') * 2)
        .fluidOutputs(fluid('decoppered_lead') * 3600)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

AUTOCLAVE.recipeBuilder()
        .inputs(ore('dustCopperDross') * 2)
        .fluidInputs(fluid('sulfuric_acid') * 1000)
        .fluidInputs(fluid('oxygen') * 3000)
        .fluidOutputs(fluid('copper_sulfate_solution') * 1000)
        //.fluidOutputs(fluid('sulfur_dioxide') * 1000) Add when susycore 0.0.14
        .EUt(120)
        .duration(200)
        .buildAndRegister()

DISTILLERY.recipeBuilder()
        .fluidInputs(fluid('copper_sulfate_solution') * 1000)
        .outputs(metaitem('dustCopperSulfate') * 6)
        .fluidOutputs(fluid('water') * 1000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

//SOFTENING (HARRIS PROCESS) (4% Sb, 2% As, 5% Sn)
REACTION_FURNACE.recipeBuilder()
        .fluidInputs(fluid('sodium_nitrate') * 720)
        .fluidInputs(fluid('decoppered_lead') * 1440)
        .fluidInputs(fluid('sodium_hydroxide') * 288)
        .outputs(metaitem('dustHarrisSlag'))
        .fluidOutputs(fluid('softened_lead') * 1440)
        .fluidOutputs(fluid('nitrogen') * 1000)
        .fluidOutputs(fluid('water') * 900)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustHarrisSlag'))
        .fluidInputs(fluid('distilled_water') * 10)
        .chancedOutput(metaitem('dustSodiumAntimonate'), 400, 0)
        .fluidOutputs(fluid('sodium_arsenate_stannate_solution') * 10)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

DISTILLERY.recipeBuilder()
        .fluidInputs(fluid('sodium_arsenate_stannate_solution') * 1000)
        .outputs(metaitem('dustSodiumArsenateStannateMixture') * 6)
        .fluidOutputs(fluid('water') * 1000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

FLUID_EXTRACTOR.recipeBuilder()
        .inputs(ore('dustSodiumArsenateStannateMixture') * 6)
        .fluidOutputs(fluid('sodium_arsenate') * 1152)
        .outputs(metaitem('dustSodiumStannate') * 25)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustSodiumArsenate') * 8)
        .fluidInputs(fluid('hydrochloric_acid') * 3000)
        .fluidOutputs(fluid('salty_arsenic_acid') * 3000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .fluidInputs(fluid('salty_arsenic_acid') * 6000)
        .outputs(metaitem('dustSaltyArsenicPentoxide') * 7)
        .fluidOutputs(fluid('steam') * 9000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustSaltyArsenicPentoxide') * 7)
        .fluidInputs(fluid('water') * 6000)
        .outputs(metaitem('dustArsenicVOxide') * 7)
        .fluidOutputs(fluid('salt_water') * 6000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustSodiumStannate') * 6)
        .fluidInputs(fluid('hydrochloric_acid') * 2000)
        .fluidInputs(fluid('distilled_water') * 1000)
        .outputs(metaitem('dustTinIvOxide') * 3)
        .fluidOutputs(fluid('diluted_saltwater') * 2000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustSodiumAntimonate') * 10)
        .fluidInputs(fluid('hydrochloric_acid') * 2000)
        .outputs(metaitem('dustAntimonyVOxide') * 7)
        .fluidOutputs(fluid('salt_water') * 2000)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

for (carbon in CarbonGlobals.sources) {
    ROASTER.recipeBuilder()
            .inputs(ore('dustAntimonyVOxide') * 7)
            .inputs(ore(carbon.name) * carbon.equivalent(5))
            .outputs(metaitem('dustAntimony') * 2)
            .fluidOutputs(fluid('carbon_monoxide') * 5000)
            .EUt(120)
            .duration(200)
            .buildAndRegister()

    ROASTER.recipeBuilder()
            .inputs(ore('dustTinIvOxide') * 3)
            .inputs(ore(carbon.name) * carbon.equivalent(2))
            .outputs(metaitem('dustTin'))
            .fluidOutputs(fluid('carbon_monoxide') * 2000)
            .EUt(120)
            .duration(200)
            .buildAndRegister()

    if (carbon.equivalent(23) <= 64) {
        ROASTER.recipeBuilder()
                .inputs(ore('dustLithargeSlag') * 10)
                .inputs(ore(carbon.name) * carbon.equivalent(23))
                .outputs(metaitem('ingotBettsCrudeLead') * 10)
                .EUt(Globals.voltAmps[3])
                .duration(400)
                .buildAndRegister()
    }
}

//SILVER REMOVAL (PARKES PROCESS)
CENTRIFUGE.recipeBuilder()
        .fluidInputs(fluid('softened_lead') * 3600)
        .fluidInputs(fluid('zinc') * 144)
        .fluidOutputs(fluid('silver_free_lead') * 3600)
        .fluidOutputs(fluid('zinc_dross') * 216)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

VACUUM_CHAMBER.recipeBuilder()
        .fluidInputs(fluid('zinc_dross') * 432)
        .outputs(metaitem('dustSilver'))
        .fluidOutputs(fluid('zinc') * 288)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

//DEZINCING
VACUUM_CHAMBER.recipeBuilder()
        .fluidInputs(fluid('silver_free_lead') * 2880)
        .fluidOutputs(fluid('dezinced_lead') * 2880)
        .fluidOutputs(fluid('zinc') * 144)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

FLUID_SOLIDIFIER.recipeBuilder()
        .fluidInputs(fluid('dezinced_lead') * 144)
        .notConsumable(metaitem('shape.mold.ingot'))
        .outputs(metaitem('ingotLead'))
        .EUt(7)
        .duration(20)
        .buildAndRegister()

//DEBISMUTHIZING (KROLL-BETTERTON PROCESS)
REACTION_FURNACE.recipeBuilder()
        .fluidInputs(fluid('silver_free_lead') * 9600)
        .inputs(ore('dustAnyPurityMagnesium') * 6)
        .inputs(ore('dustAnyPurityCalcium') * 3)
        .fluidOutputs(fluid('lead') * 9600)
        .fluidOutputs(fluid('bismuth_dross') * 1440)
        .fluidOutputs(fluid('antimony_dross') * 720)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

BR.recipeBuilder()
        .fluidInputs(fluid('bismuth_dross') * 720)
        .fluidInputs(fluid('chlorine') * 6000)
        .outputs(metaitem('dustBismuth') * 2)
        .outputs(metaitem('dustMagnesiumChloride') * 6)
        .outputs(metaitem('dustCalciumChloride') * 3)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

//HIGH PURITY LEAD (BETTS PROCESS)
ELECTROLYTIC_CELL.recipeBuilder()
        .notConsumable(metaitem('plateLead'))
        .notConsumable(fluid('hexafluorosilicic_acid') * 1000)
        .inputs(ore('plateCrudeLead') * 2)
        .fluidInputs(fluid('distilled_water') * 1000)
        .chancedOutput(metaitem('dustHighPurityLead') * 2, 9800, 0)
        .outputs(metaitem('anode_slime.lead'))
        .fluidOutputs(fluid('oxygen') * 1000)
        .EUt(256)
        .duration(400)
        .buildAndRegister()

//SLIME TREATMENT
ROASTER.recipeBuilder()
        .inputs(metaitem('anode_slime.lead'))
        .outputs(metaitem('dustBlackMetal'))
        .EUt(Globals.voltAmps[3])
        .duration(400)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .inputs(ore('dustBlackMetal') * 50)
        .fluidInputs(fluid('oxygen') * 9000)
        .fluidOutputs(fluid('antimony_arsenic_flue_gas') * 1000)
        .outputs(metaitem('dustBurnedBlackMetal') * 2)
        .EUt(Globals.voltAmps[3])
        .duration(400)
        .buildAndRegister()

SIFTER.recipeBuilder()
        .notConsumable(metaitem('item_filter'))
        .fluidInputs(fluid('antimony_arsenic_flue_gas') * 1000)
        .outputs(metaitem('dustCadmiumRichFlue') * 3)
        .fluidOutputs(fluid('flue_gas') * 1000)
        .EUt(120)
        .duration(160)
        .buildAndRegister()

REACTION_FURNACE.recipeBuilder()
        .inputs(ore('dustBurnedBlackMetal') * 2)
        .fluidInputs(fluid('oxygen') * 2500)
        .outputs(metaitem('dustSilver'))
        .outputs(metaitem('dustLithargeSlag') * 5)
        .EUt(Globals.voltAmps[3])
        .duration(400)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustAnyPuritySulfur'))
        .fluidInputs(fluid('betts_crude_lead') * 1440)
        .outputs(metaitem('dustCopperDross') * 2)
        .fluidOutputs(fluid('decoppered_betts_lead') * 1296)
        .EUt(Globals.voltAmps[3])
        .duration(400)
        .buildAndRegister()

REACTION_FURNACE.recipeBuilder()
        .fluidInputs(fluid('decoppered_betts_lead') * 2592)
        .inputs(ore('dustAnyPurityMagnesium') * 6)
        .inputs(ore('dustAnyPurityCalcium') * 3)
        .fluidOutputs(fluid('lead') * 1728)
        .fluidOutputs(fluid('bismuth_dross') * 2160)
        .EUt(Globals.voltAmps[3])
        .duration(800)
        .buildAndRegister()

ELECTROLYZER.recipeBuilder()
        .fluidInputs(fluid('lead_chloride') * 432)
        .notConsumable(metaitem('graphite_electrode') * 1)
        .notConsumable(metaitem('plateLead') * 1)
        .outputs(metaitem('dustLead') * 1)
        .fluidOutputs(fluid('chlorine') * 2000)
        .duration(160)
        .EUt(30)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .inputs(ore('dustLeadIiHydroxide') * 5)
        .outputs(metaitem('dustLeadOxide') * 2)
        .fluidOutputs(fluid('steam') * 1000)
        .duration(80)
        .EUt(30)
        .buildAndRegister()

EBF.recipeBuilder()
        .inputs(ore('dustLeadOxide') * 2)
        .fluidInputs(fluid('carbon_monoxide') * 1000)
        .outputs(metaitem('dustLead'))
        .fluidOutputs(fluid('carbon_dioxide') * 1000)
        .blastFurnaceTemp(1200)
        .duration(80)
        .EUt(120)
        .buildAndRegister()
