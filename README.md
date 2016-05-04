<h2><strong><i>Halophila johnsonii</i> Individual Based Model</strong></h2>

<h3><strong>Title:</strong> Morphological effects of light reduction on the seagrass Halophila johnsonii simulated using an individual-based model</h3>
<h5><strong>Vincent Scavetta and Courtney Richmond.</strong><br>Department of Biological Sciences, Rowan University</h5>
<p><strong>Abstract:</strong> Seagrasses are marine flowering plants that live entirely underwater. 
They reproduce both asexually and sexually, growing by rhizome extension. 
Seagrass meadows are globally distributed in areas with high light levels and clear water, within intertidal and shallow subtidal zones.
Due to their light constraints, growth is restricted to the photic zone and is not possible in deeper waters. Seagrasses are ecological 
engineers, altering the environment by changing the water flow, nutrient cycling, sediment stabilization, and providing refuge for benthic
organisms.  In recent decades, global seagrasses have dramatically declined in density, extent, and biodiversity. Multiple stressors have 
directly contributed to these declines, including climate changes, shifts in water quality, and increased contaminants entering coastal 
waters. Conducting studies on why seagrass populations are declining can be complex, time consuming, and costly. Designing a simulation 
model with data collected from field and lab experiments allows us to mimic realistic environmental changes and predict their impacts on 
a model organism. Models can predict how populations should react based on stochastic stimuli such as the intensity of light reaching 
seagrasses. Individual-based models (IBMs) are used to predict population-level impacts by modeling how individuals react to abiotic and 
biotic conditions in their local environment in terms of their survival, growth, and reproduction, each resulting from the individuals’ 
unique behavior, genetics, and experiences. Halophila johnsonii is a rare threatened seagrass located on a 200km stretch of Atlantic
Floridian coast.  This species has relatively low biomass, a high turnover rate, and is tolerant to fluctuations in temperature and 
salinity. We constructed an IBM to predict how H. johnsonii should be impacted by a variety of light conditions that resemble natural 
and anthropogenically-driven reductions in light levels. The model predicts that seagrass patches will decrease in density and extent 
when exposed to more severe and/or more frequent light reduction. This suggests that without improvements in water quality, 
H. johnsonii’s density and distribution should decrease. While this species is relatively small-bodied, it provides ecosystem services 
in shallower waters than many species are able to inhabit, increasing concerns about the impacts of its decline.
</p>
<p><b>The Model: </b>The java implementation of this model was written at Rowan University by Vincent Scavetta under the supervision of
Dr. Courtney Richmond. The code was initially written in FORTRAN 90 by Dr. Richmond in conjunction with Dr. Rose. The model uses real growth data
collected from field and lab based experiments on the seagrass <i>H. johnsonii</i>. Solar data was collected from the NOAA database measured
at Miami station #722030 (West Palm Beach Intl Arpt, FL). The raw solar data was transformed to produce a value relative to the maximum solar irradiance recorded. These
transformed values were implemented into the mode to simulate typical irradiance values throughout a given year.</p>
<p>In its current state, the model is very basic in its nature. There is currently only one variable for light reduction that is adjusted
thoughout the course of the simulation. By the end of this project, we hope to have a more realistic simulation that takes into account the
following items:
<ol>
  <li>Shading due to other biomass in the area</li>
  <li>Variable storm lengths</li>
  <li>Water level calculations to account forlunar tides</li>
  <li>Localized effects such as boat traffic and dock shading</li>
</ol>
</p>
