import globals.Globals
import globals.CarbonGlobals

MIXER = recipemap('mixer')
FF = recipemap('froth_flotation')
CLARIFIER = recipemap('clarifier')
CENTRIFUGE = recipemap('centrifuge')
ELECTROMAGNETIC_SEPARATOR = recipemap('electromagnetic_separator')
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

CENTRIFUGE.recipeBuilder()
        .fluidInputs(fluid('hafnium_extract') * 1000)
        .fluidInputs(fluid('diluted_sulfuric_acid') * 80)
        .fluidOutputs(fluid('hafnium_extraction_mixture') * 1000)
        .fluidOutputs(fluid('hafnium_sulfate_solution') * 40)
        .EUt(Globals.voltAmps[3])
        .duration(20)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .fluidInputs(fluid('hafnium_sulfate_solution') * 2000)
        .outputs(metaitem('dustHafniumDioxide') * 3)
        .fluidOutputs(fluid('steam') * 2000)
        .fluidOutputs(fluid('sulfur_trioxide') * 2000)
        .EUt(Globals.voltAmps[1])
        .duration(20)
        .buildAndRegister()

for (highPurityCombustible in CarbonGlobals.highPurityCombustibles()) {
        FLUIDIZED_BED_REACTOR.recipeBuilder()
                .inputs(ore('dustHafniumDioxide'))
                .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(2))
                .fluidInputs(fluid('chlorine') * 4000)
                .outputs(metaitem('dustImpureHafniumTetrachloride') * 5)
                .fluidOutputs(fluid('carbon_monoxide') * 2000)
                .duration(200 * highPurityCombustible.duration)
                .EUt(Globals.voltAmps[3])
                .buildAndRegister()
}

REACTION_FURNACE.recipeBuilder()
        .inputs(ore('dustImpureHafniumTetrachloride') * 5)
        .notConsumable(fluid('nitrogen') * 1000)
        .notConsumable(fluid('hydrogen') * 1000)
        .fluidOutputs(fluid('hafnium_tetrachloride') * 720)
        .duration(100)
        .EUt(Globals.voltAmps[4])
        .buildAndRegister()

for (inertGas in Globals.inertGases) {
        EBF.recipeBuilder()
                .circuitMeta(2)
                .fluidInputs(fluid('hafnium_tetrachloride') * 720)
                .notConsumable(fluid(inertGas.name) * inertGas.amount_required)
                .inputs(ore('dustAnyPurityMagnesium') * 2)
                .outputs(metaitem('sponge.hafnium.crude'))
                .fluidOutputs(fluid('magnesium_chloride') * 864)
                .blastFurnaceTemp(2150)
                .duration(100 * inertGas.duration)
                .EUt(Globals.voltAmps[4])
                .buildAndRegister()
}

VACUUM_CHAMBER.recipeBuilder()
        .inputs(metaitem('sponge.hafnium.crude'))
        .outputs(metaitem('sponge.hafnium'))
        .duration(100)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

MACERATOR.recipeBuilder()
        .inputs(metaitem('sponge.hafnium'))
        .outputs(metaitem('dustHafnium'))
        .duration(100)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

//VAN ARKEL-DE BOER

BR.recipeBuilder()
        .inputs(ore('dustHafnium'))
        .inputs(ore('dustAnyPurityIodine') * 4)
        .outputs(metaitem('dustHafniumIodide') * 5)
        .duration(360)
        .EUt(Globals.voltAmps[3])
        .buildAndRegister()

CVD.recipeBuilder()
        .inputs(ore('dustHafniumIodide') * 5)
        .notConsumable(metaitem('stickTungsten'))
        .outputs(metaitem('dustHighPurityHafnium'))
        .fluidOutputs(fluid('iodine') * 576)
        .duration(180)
        .EUt(Globals.voltAmps[4])
        .buildAndRegister()
