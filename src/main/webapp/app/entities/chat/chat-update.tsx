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
import { getEntity, updateEntity, createEntity, reset } from './chat.reducer';
import { IChat } from 'app/shared/model/chat.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IChatUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChatUpdate = (props: IChatUpdateProps) => {
  const [baseId, setBaseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { chatEntity, bases, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/chat');
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
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...chatEntity,
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
          <h2 id="touchbaseApp.chat.home.createOrEditLabel">
            <Translate contentKey="touchbaseApp.chat.home.createOrEditLabel">Create or edit a Chat</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : chatEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="chat-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="chat-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="createdDateLabel" for="chat-createdDate">
                  <Translate contentKey="touchbaseApp.chat.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="chat-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.chatEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="chat-lastModifiedDate">
                  <Translate contentKey="touchbaseApp.chat.lastModifiedDate">Last Modified Date</Translate>
                </Label>
                <AvInput
                  id="chat-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.chatEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="chatpiChatIdLabel" for="chat-chatpiChatId">
                  <Translate contentKey="touchbaseApp.chat.chatpiChatId">Chatpi Chat Id</Translate>
                </Label>
                <AvField id="chat-chatpiChatId" type="text" name="chatpiChatId" />
              </AvGroup>
              <AvGroup>
                <Label for="chat-base">
                  <Translate contentKey="touchbaseApp.chat.base">Base</Translate>
                </Label>
                <AvInput id="chat-base" type="select" className="form-control" name="baseId">
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
              <Button tag={Link} id="cancel-save" to="/chat" replace color="info">
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
  chatEntity: storeState.chat.entity,
  loading: storeState.chat.loading,
  updating: storeState.chat.updating,
  updateSuccess: storeState.chat.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ChatUpdate);
