package be.ambrosia.engine.ecs.components

import be.ambrosia.engine.particles.Particle

class ParticleEmitter : TemplateComp {
    lateinit var emit: () -> Particle
}