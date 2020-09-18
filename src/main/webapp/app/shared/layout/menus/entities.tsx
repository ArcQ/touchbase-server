import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/base">
      <Translate contentKey="global.menu.entities.base" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/chat">
      <Translate contentKey="global.menu.entities.chat" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/mission">
      <Translate contentKey="global.menu.entities.mission" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/base-member">
      <Translate contentKey="global.menu.entities.baseMember" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/base-join">
      <Translate contentKey="global.menu.entities.baseJoin" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
