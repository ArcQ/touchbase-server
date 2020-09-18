import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IBase } from 'app/shared/model/base.model';
import { getEntities as getBases } from 'app/entities/base/base.reducer';
import { getEntity, updateEntity, createEntity, reset } from './base-member.reducer';
import { IBaseMember } from 'app/shared/model/base-member.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBaseMemberUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BaseMemberUpdate = (props: IBaseMemberUpdateProps) => {
  const [memberId, setMemberId] = useState('0');
  const [baseId, setBaseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { baseMemberEntity, users, bases, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/base-member');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getBases();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    if (errors.length === 0) {
      const entity = {
        ...baseMemberEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="touchbaseApp.baseMember.home.createOrEditLabel">
            <Translate contentKey="touchbaseApp.baseMember.home.createOrEditLabel">Create or edit a BaseMember</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : baseMemberEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="base-member-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="base-member-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="createdAtLabel" for="base-member-createdAt">
                  <Translate contentKey="touchbaseApp.baseMember.createdAt">Created At</Translate>
                </Label>
                <AvInput
                  id="base-member-createdAt"
                  type="datetime-local"
                  className="form-control"
                  name="createdAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.baseMemberEntity.createdAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedAtLabel" for="base-member-updatedAt">
                  <Translate contentKey="touchbaseApp.baseMember.updatedAt">Updated At</Translate>
                </Label>
                <AvInput
                  id="base-member-updatedAt"
                  type="datetime-local"
                  className="form-control"
                  name="updatedAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.baseMemberEntity.updatedAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="roleLabel" for="base-member-role">
                  <Translate contentKey="touchbaseApp.baseMember.role">Role</Translate>
                </Label>
                <AvInput
                  id="base-member-role"
                  type="select"
                  className="form-control"
                  name="role"
                  value={(!isNew && baseMemberEntity.role) || 'MEMBER'}
                >
                  <option value="MEMBER">{translate('touchbaseApp.Role.MEMBER')}</option>
                  <option value="ADMIN">{translate('touchbaseApp.Role.ADMIN')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="base-member-member">
                  <Translate contentKey="touchbaseApp.baseMember.member">Member</Translate>
                </Label>
                <AvInput id="base-member-member" type="select" className="form-control" name="memberId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="base-member-base">
                  <Translate contentKey="touchbaseApp.baseMember.base">Base</Translate>
                </Label>
                <AvInput id="base-member-base" type="select" className="form-control" name="baseId">
                  <option value="" key="0" />
                  {bases
                    ? bases.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/base-member" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  bases: storeState.base.entities,
  baseMemberEntity: storeState.baseMember.entity,
  loading: storeState.baseMember.loading,
  updating: storeState.baseMember.updating,
  updateSuccess: storeState.baseMember.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getBases,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseMemberUpdate);
