/**
 * Copyright (C) 2013 Colorado School of Mines
 *
 * This file is part of the Interface Software Development Kit (SDK).
 *
 * The InterfaceSDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The InterfaceSDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the InterfaceSDK.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.mines.acmX.exhibit.module_management.metas;

/**
 * Enum used by ModuleManager in its checkDependencies function. Used to
 * differentiate between modules that have already been checked or not.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */
public enum CheckType {

    CHECKED, // Module has been checked
    DIRTY,   // Module is in the process of being checked
    UNKNOWN  // Module has not yet been encountered

}


