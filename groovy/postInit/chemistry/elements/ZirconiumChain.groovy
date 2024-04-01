import globals.Globals
import globals.CarbonGlobals

MIXER = recipemap('mixer')
FF = recipemap('froth_flotation')
CLARIFIER = recipemap('clarifier')
CENTRIFUGE = recipemap('centrifuge')
ELECTROSTATIC_SEPARATOR = recipemap('electrostatic_separator')
BR = recipemap('batch_reactor')
CSTR = recipemap('continuous_stirred_tank_reactor')
SIFTER = recipemap('sifter')
FLUIDIZED_BED_REACTOR = recipemap('fluidized_bed_reactor')
VACUUM_FREEZER = recipemap('vacuum_freezer')
DISTILLATION_TOWER = recipemap('distillation_tower')
VACUUM_CHAMBER = recipemap('vacuum_chamber')
MACERATOR = recipemap('macerator')
FIXED_BED_REACTOR = recipemap('fixed_bed_reactor')
EBF = recipemap('electric_blast_furnace')
DISTILLERY = recipemap('distillery')
GRAVITY_SEPARATOR = recipemap('gravity_separator')
ADVANCED_ARC_FURNACE = recipemap('advanced_arc_furnace')
ROASTER = recipemap('roaster')

// Benefication

// Zircon
GRAVITY_SEPARATOR.recipeBuilder()
        .inputs(ore('dustZircon'))
        .outputs(metaitem('dustSiftedZircon'))
        .chancedOutput(metaitem('dustCertusQuartz'), 5000, 0)
        .EUt(Globals.voltAmps[1])
        .duration(40)
        .buildAndRegister()

MIXER.recipeBuilder()
        .inputs(ore('dustSiftedZircon') * 8)
        .fluidInputs(fluid('distilled_water') * 2000)
        .fluidOutputs(fluid('impure_zircon_slurry') * 2000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FF.recipeBuilder()
        .fluidInputs(fluid('impure_zircon_slurry') * 2000)
        .notConsumable(fluid('soda_ash_solution') * 1000)
        .notConsumable(fluid('alkaline_sodium_oleate_solution') * 1000)
        .notConsumable(ore('dustSodiumSilicate') * 6)
        .notConsumable(ore('dustIronIiiChloride') * 4)
        .notConsumable(ore('dustSodiumSulfide') * 3)
        .fluidOutputs(fluid('zircon_slurry') * 1000)
        .fluidOutputs(fluid('pegmatite_tailing_slurry') * 1000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('zircon_slurry') * 1000)
        .outputs(metaitem('dustFlotatedZircon') * 16)
        .fluidOutputs(fluid('wastewater') * 1000)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

ELECTROSTATIC_SEPARATOR.recipeBuilder()
        .inputs(ore('dustFlotatedZircon'))
        .outputs(metaitem('dustConcentrateZircon'))
        .chancedOutput(metaitem('dustIlmenite'), 500, 100)
        .chancedOutput(metaitem('dustChromite'), 500, 100)
        .chancedOutput(metaitem('dustHematite'), 500, 100)
        .chancedOutput(metaitem('dustRutile'), 500, 100)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

// Thermal disassociation (90%)
ADVANCED_ARC_FURNACE.recipeBuilder()
        .inputs(ore('dustConcentrateZircon'))
        .chancedOutput(metaitem('dustFusedZirconia') * 3, 9000, 0)
        .outputs(metaitem('dustSiliconDioxide') * 3)
        .duration(20)
        .EUt(Globals.voltAmps[4])
        .buildAndRegister()

// Carbochlorination (100%)
for (highPurityCombustible in CarbonGlobals.highPurityCombustibles()) {
        FLUIDIZED_BED_REACTOR.recipeBuilder()
                .inputs(ore('dustConcentrateZircon'))
                .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(4))
                .fluidInputs(fluid('chlorine') * 8000)
                .outputs(metaitem('dustImpureZirconiumTetrachloride') * 5)
                .chancedOutput(metaitem(highPurityCombustible.byproduct), 1000, 0)
                .chancedOutput(metaitem('dustCobalt'), 500, 100)
                .fluidOutputs(fluid('silicon_tetrachloride') * 1000)
                .fluidOutputs(fluid('carbon_monoxide') * 4000)
                .duration(40 * highPurityCombustible.duration)
                .EUt(Globals.voltAmps[3])
                .buildAndRegister()
}

// Baddeleyite
ADVANCED_ARC_FURNACE.recipeBuilder()
        .inputs(ore('dustBaddeleyite'))
        .chancedOutput(metaitem('dustFusedZirconia') * 3, 9000, 0)
        .duration(20)
        .EUt(Globals.voltAmps[5])
        .buildAndRegister()

// Direct sulfatization (75%)
BR.recipeBuilder()
        .inputs(ore('dustFusedZirconia') * 3)
        .fluidInputs(fluid('sulfuric_acid') * 1500)
        .fluidInputs(fluid('distilled_water') * 750)
        .fluidOutputs(fluid('zirconium_sulfate_solution') * 2250)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

// Calcium zirconate sulfatization (90%)

ROASTER.recipeBuilder()
        .inputs(ore('dustFusedZirconia') * 3)
        .inputs(ore('dustQuicklime') * 2)
        .chancedOutput(metaitem('dustCalciumZirconate') * 5, 9000, 0)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustCalciumZirconate') * 3)
        .fluidInputs(fluid('sulfuric_acid') * 3000)
        .fluidOutputs(fluid('zirconium_sulfate_solution') * 3000)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustCalciumChloride') * 6)
        .fluidInputs(fluid('zirconium_sulfate_solution') * 3000)
        .outputs(metaitem('dustCalciumSulfate') * 6)
        .fluidOutputs(fluid('zirconyl_chloride_solution') * 2000)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

// Carbochlorination (100%)
for (highPurityCombustible in CarbonGlobals.highPurityCombustibles()) {
        FLUIDIZED_BED_REACTOR.recipeBuilder()
                .inputs(ore('dustBaddeleyite'))
                .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(2))
                .fluidInputs(fluid('chlorine') * 4000)
                .outputs(metaitem('dustImpureZirconiumTetrachloride') * 5)
                .chancedOutput(metaitem(highPurityCombustible.byproduct), 1000, 0)
                .fluidOutputs(fluid('carbon_monoxide') * 2000)
                .duration(40 * highPurityCombustible.duration)
                .EUt(Globals.voltAmps[3])
                .buildAndRegister()
}

// Hafnium separation
BR.recipeBuilder()
        .inputs(ore('dustImpureZirconiumTetrachloride') * 5)
        .fluidInputs(fluid('distilled_water') * 3000)
        .fluidOutputs(fluid('zirconyl_chloride_solution') * 2000)
        .duration(40)
        .EUt(Globals.voltAmps[2])
        .buildAndRegister()

MIXER.recipeBuilder()
        .inputs(ore('dustAmmoniumThiocyanate') * 8)
        .fluidInputs(fluid('kerosene') * 900)
        .fluidInputs(fluid('methyl_isobutyl_ketone') * 100)
        .fluidOutputs(fluid('hafnium_extraction_mixture') * 1000)
        .EUt(Globals.voltAmps[2])
        .duration(200)
        .buildAndRegister()

CENTRIFUGE.recipeBuilder()
        .fluidInputs(fluid('zirconyl_chloride_solution') * 2000)
        .fluidInputs(fluid('hafnium_extraction_mixture') * 1000)
        .fluidOutputs(fluid('purified_zirconyl_chloride_solution') * 2000)
        .fluidOutputs(fluid('hafnium_extract') * 1000)
        .EUt(Globals.voltAmps[3])
        .duration(20)
        .buildAndRegister()

LCR.recipeBuilder()
        .fluidInputs(fluid('purified_zirconyl_chloride_solution') * 10000)
        .fluidInputs(fluid('sulfuric_acid') * 2000)
        .fluidInputs(fluid('distilled_water') * 13000)
        .notConsumable(fluid('ammonia_solution') * 1000)
        .outputs(metaitem('dustZirconiumBasicSulfate') * 23)
        .fluidOutputs(fluid('hydrochloric_acid') * 12000)
        .EUt(Globals.voltAmps[3])
        .duration(20)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustZirconiumBasicSulfate') * 23)
        .fluidInputs(fluid('ammonia_solution') * 4000)
        .outputs(metaitem('dustZirconiumDioxide') * 15)
        .fluidOutputs(fluid('ammonium_sulfate_solution') * 2000)
        .EUt(Globals.voltAmps[1])
        .duration(20)
        .buildAndRegister()

for (highPurityCombustible in CarbonGlobals.highPurityCombustibles()) {
        FLUIDIZED_BED_REACTOR.recipeBuilder()
                .inputs(ore('dustZirconiumDioxide'))
                .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(2))
                .fluidInputs(fluid('chlorine') * 4000)
                .outputs(metaitem('dustZirconiumTetrachloride') * 5)
                .fluidOutputs(fluid('carbon_monoxide') * 2000)
                .duration(40 * highPurityCombustible.duration)
                .EUt(Globals.voltAmps[3])
                .buildAndRegister()
}

for (inertGas in Globals.inertGases) {
        EBF.recipeBuilder()
                .circuitMeta(2)
                .inputs(ore('dustZirconiumTetrachloride') * 5)
                .notConsumable(fluid(inertGas.name) * inertGas.amount_required)
                .inputs(ore('dustAnyPurityMagnesium') * 2)
                .outputs(metaitem('sponge.zirconium.crude'))
                .fluidOutputs(fluid('magnesium_chloride') * 864)
                .blastFurnaceTemp(2150)
                .duration(20 * inertGas.duration)
                .EUt(Globals.voltAmps[4])
                .buildAndRegister()
}

VACUUM_CHAMBER.recipeBuilder()
        .inputs(metaitem('sponge.zirconium.crude'))
        .outputs(metaitem('sponge.zirconium'))
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

MACERATOR.recipeBuilder()
        .inputs(metaitem('sponge.zirconium'))
        .outputs(metaitem('dustZirconium'))
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

//VAN ARKEL-DE BOER

BR.recipeBuilder()
        .inputs(ore('dustZirconium'))
        .inputs(ore('dustAnyPurityIodine') * 4)
        .outputs(metaitem('dustZirconiumIodide') * 5)
        .duration(300)
        .EUt(Globals.voltAmps[3])
        .buildAndRegister()

CVD.recipeBuilder()
        .inputs(ore('dustZirconiumIodide') * 5)
        .notConsumable(metaitem('stickTungsten'))
        .outputs(metaitem('dustHighPurityZirconium'))
        .fluidOutputs(fluid('iodine') * 576)
        .duration(150)
        .EUt(Globals.voltAmps[4])
        .buildAndRegister()
