import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBase } from 'app/shared/model/base.model';
import { getEntities as getBases } from 'app/entities/base/base.reducer';
import { getEntity, updateEntity, createEntity, reset } from './base-join.reducer';
import { IBaseJoin } from 'app/shared/model/base-join.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBaseJoinUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BaseJoinUpdate = (props: IBaseJoinUpdateProps) => {
  const [baseId, setBaseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { baseJoinEntity, bases, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/base-join');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBases();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...baseJoinEntity,
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
          <h2 id="touchbaseApp.baseJoin.home.createOrEditLabel">
            <Translate contentKey="touchbaseApp.baseJoin.home.createOrEditLabel">Create or edit a BaseJoin</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : baseJoinEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="base-join-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="base-join-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="baseJoinLabel" for="base-join-baseJoin">
                  <Translate contentKey="touchbaseApp.baseJoin.baseJoin">Base Join</Translate>
                </Label>
                <AvInput
                  id="base-join-baseJoin"
                  type="select"
                  className="form-control"
                  name="baseJoin"
                  value={(!isNew && baseJoinEntity.baseJoin) || 'REQUEST'}
                >
                  <option value="REQUEST">{translate('touchbaseApp.BaseJoinAction.REQUEST')}</option>
                  <option value="INVITE">{translate('touchbaseApp.BaseJoinAction.INVITE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="base-join-base">
                  <Translate contentKey="touchbaseApp.baseJoin.base">Base</Translate>
                </Label>
                <AvInput id="base-join-base" type="select" className="form-control" name="baseId">
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
              <Button tag={Link} id="cancel-save" to="/base-join" replace color="info">
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
  bases: storeState.base.entities,
  baseJoinEntity: storeState.baseJoin.entity,
  loading: storeState.baseJoin.loading,
  updating: storeState.baseJoin.updating,
  updateSuccess: storeState.baseJoin.updateSuccess,
});

const mapDispatchToProps = {
  getBases,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseJoinUpdate);
